#puts "Hello"
#puts "It is now #{Time.now}"

#puts "gin joint".length
#puts "Rick".index("c")
#puts 42.even?
#puts sam.play(song)

#num = -1234
#positive = num.abs  
#puts positive

#def say_goodnight(name)
#  result = "Good night, " + name
#  return result
#end
def say_goodnight(name)
  result = "Good night, #{name.capitalize}"
end
# Time for bed...
#puts say_goodnight("JohnBoy")
#puts(say_goodnight("MaryEllen"))
#puts say_goodnight "Marky"
#puts say_goodnight 'Marky'
#puts say_goodnight "\nGrandma"
#puts say_goodnight '\nGrandma'
#puts say_goodnight "marky"
#puts say_goodnight 'marky'

#a = [ 1, 'cat', 3.14 ] # array with three elements
#puts "The first element is #{a[0]}"
## set the third element
#a[2] = nil
#puts "The array is now #{a.inspect}"

a = [ 'ant', 'bee', 'cat', 'dog', 'elk' ]
a[0] # => "ant"
a[3] # => "dog"
puts "The array is now #{a.inspect}"
# this is the same:
a = %w{ ant bee cat dog elk }
a[0] # => "ant"
a[3] # => "dog"
puts "The array is now #{a.inspect}"