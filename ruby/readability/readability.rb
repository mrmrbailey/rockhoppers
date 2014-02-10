require 'watir-webdriver'

def log_on(browser, user, password)
  puts "log_on"
  browser.text_field(:id => 'username').set "#{user}"
  browser.text_field(:id => 'password').set "#{password}"
  browser.input(:type => 'submit').click
end

def delete_article(browser)
  puts "delete_article"
  link = browser.link :class => 'article-delete'
  link.exists?
  link.click
  Watir::Wait.until { browser.text.include? 'Delete this article?' }
  browser.button(:class => 'confirm').click
end



b = Watir::Browser.new

b.goto 'http://www.readability.com/login'
log_on(b,'rockhoppers','county')
b.goto 'http://www.readability.com/rockhoppers/archives'
for j in 0..29
  delete_article(b)
end
