require 'rubygems'
require 'mechanize'
require 'nokogiri'
require 'open-uri'
require 'csv'


# abort "#{$0} login passwd" if (ARGV.size != 2)

a = Mechanize.new { |agent|
  # refreshes after login
  agent.follow_meta_refresh = true
}

a.get('http://www.fftoolbox.com/index.cfm') do |home_page|
  signin_page = a.click(home_page.link_with(:text => 'Fantasy Football'))
  cheatsheet_page = a.click(signin_page.link_with(:text => '2013 Fantasy Football Cheat Sheets'))
  
  # the cheatsheet page is the QB's but lets click it to be sure
  qb_page = a.click(cheatsheet_page.link_with(:text => 'QB'))
  
  table = qb_page.parser.xpath("//table[@class='grid']//tr[not(@class='header')]")
  puts table.to_html

  10.times do |i|
    p table.xpath("tr[2]").size
  end
#  table = qb_page.parser.xpath("//table[@class='grid']//tr[not(@class='header')]")
#  puts table.to_html
  
  
# row_data = doc.css('.table.draggable.table-striped.table-hover tr.strong td').map do |tdata|
#   tdata.text
# end
  

  
end

