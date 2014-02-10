require 'rubygems'
require 'nokogiri'
require 'open-uri'
require 'csv'
require 'mechanize'

agent = Mechanize.new

LOGIN_URL = "http://www.fftoolbox.com/customize/login.cfm"
agent.get(LOGIN_URL)


agent.page.forms[0]["Username"] = "rockhoppers"
agent.page.forms[0]["Password"] = "county"
agent.page.forms[0].submit


# PAGE_URL = "http://www.fftoolbox.com/football/2013/overall.cfm?league_ID=474199"
PAGE_URL = "http://www.fftoolbox.com/football/2013/overall.cfm"

page = agent.get(PAGE_URL)
# page = Nokogiri::HTML(open(PAGE_URL))

# puts page.css("table")[0].name   # => title
# puts page.css("table")[0].text   # => My webpage

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
  51.times do |i|
    csv << [table[i].Rank,table[i].Player,table[i].Pos,table[i].NFLTeam]
  end
end

puts players

# puts table# [49]
