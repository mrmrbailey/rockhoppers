require 'archive'

class ArchiveList

attr_reader :link

  def initialize link
    @link = link
  end

  def add_link link
    @link << Link.new(link)
  end

end