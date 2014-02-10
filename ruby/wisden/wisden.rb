require 'rubygems'
require 'pdf/reader'

class TestItReceiver
  def show_text_with_positioning(array, *params)
    # make use of the show text method we already have
    # assuming we don't care about positioning right now and just want the text
    show_text(array.select{|i| i.is_a?(String)}.join(""), params)
  end
end


receiver = TestItReceiver.new
pdf = PDF::Reader.file("./wisden_1985-2013_full.pdf", receiver)

