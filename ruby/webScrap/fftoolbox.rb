require 'rubygems'
require 'mechanize'
require 'nokogiri'
require 'open-uri'
require 'csv'

def parse200Table(page)
  header, *rest = (page/"tr").map do |row|
    row.children.map do |c|
      c.text
    end
  end

  header.map! do |str| str.to_sym end
  item_struct = Struct.new(*header)
  table = rest.map do |row|
    item_struct.new(*row)
  end

  players = CSV.generate do |csv|
    50.times do |i|
      csv << [table[i].Rank,table[i].Player,table[i].Pos,table[i].NFLTeam]
    end
  end
end

def parsePlayerTable(page, pos)
  header, *rest = (page/"tr").map do |row|
    row.children.map do |c|
      c.text
    end
  end

  header.map! do |str| str.to_sym end
  item_struct = Struct.new(*header)
  table = rest.map do |row|
    item_struct.new(*row)
  end
  
#  p table

end

abort "#{$0} login passwd" if (ARGV.size != 2)

a = Mechanize.new { |agent|
  # refreshes after login
  agent.follow_meta_refresh = true
}

a.get('http://www.fftoolbox.com/index.cfm') do |home_page|
  signin_page = a.click(home_page.link_with(:text => /Log in/))

  my_page = signin_page.form_with(:name => 'login') do |form|
    form.Username  = ARGV[0]
    form.Password = ARGV[1]
  end.submit

#  puts my_page.parser.css("title").text 
  
  # Click the draft page
  cheatsheet_page = a.click(my_page.link_with(:text => 'Draft'))
  
  # the cheatsheet page is the QB's but lets click it to be sure
  qb_page = a.click(cheatsheet_page.link_with(:text => 'QB'))
  puts parsePlayerTable(qb_page, 'QB')
  
  rb_page = a.click(cheatsheet_page.link_with(:text => 'RB'))
  puts parsePlayerTable(rb_page, 'RB')

  wr_page = a.click(cheatsheet_page.link_with(:text => 'WR'))
  puts parsePlayerTable(wr_page, 'WB')

  te_page = a.click(cheatsheet_page.link_with(:text => 'TE'))
  puts parsePlayerTable(te_page, 'TE')
  
  k_page = a.click(cheatsheet_page.link_with(:text => 'K'))
  puts parsePlayerTable(k_page, 'K')
  
  def_page = a.click(cheatsheet_page.link_with(:text => 'DEF'))
  puts parsePlayerTable(def_page, 'TM')

#  puts cheatsheet_page.parser.css("title").text  
#  puts cheatsheet_page.uri.to_s 
  
  top200_page = a.click(cheatsheet_page.link_with(:text => 'Top 200'))

#  puts top200_page.parser.css("title").text  
#  puts top200_page.uri.to_s 
  
  top200players = parse200Table(top200_page)
 
  3.times do |i|
    top200_page = a.click(top200_page.link_with(:text => /Next Page/))
    top200players << parse200Table(top200_page)
  end
  
  puts top200players

end

