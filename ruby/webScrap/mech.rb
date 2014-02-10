require 'rubygems'
require 'mechanize'
require 'nokogiri'
require 'open-uri'
require 'csv'

def parseTable(page)
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

abort "#{$0} login passwd" if (ARGV.size != 2)

a = Mechanize.new { |agent|
  # Flickr refreshes after login
  agent.follow_meta_refresh = true
}

a.get('http://www.fftoolbox.com/index.cfm') do |home_page|
  signin_page = a.click(home_page.link_with(:text => /Log in/))

  my_page = signin_page.form_with(:name => 'login') do |form|
    form.Username  = ARGV[0]
    form.Password = ARGV[1]
  end.submit

  puts my_page.parser.css("title").text 
  
  # Click the draft page
  cheatsheet_page = a.click(my_page.link_with(:text => 'Draft'))
  
  puts cheatsheet_page.parser.css("title").text  
  puts cheatsheet_page.uri.to_s 
  
  top200_page = a.click(cheatsheet_page.link_with(:text => 'Top 200'))

  puts top200_page.parser.css("title").text  
  puts top200_page.uri.to_s 
  
  players = parseTable(top200_page)
#  puts players
  
  3.times do |i|
    top200_page = a.click(top200_page.link_with(:text => /Next Page/))
    players << parseTable(top200_page)
#    puts players
  end
  
  puts players
end

