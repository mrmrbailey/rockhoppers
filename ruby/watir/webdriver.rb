require 'watir-webdriver'

def log_on(browser, user, password)
    puts "log_on"
  browser.text_field(:id => 'username').set "#{user}"
  browser.text_field(:id => 'password').set "#{password}"
  browser.button(:id => 'signInButton').click
end

b = Watir::Browser.new

b.goto 'https://playerwb01-t02-alwst407.test.cis.camelot/player/user/login.do'
log_on(b,'mxbailey','Camel0t')
#b.close

