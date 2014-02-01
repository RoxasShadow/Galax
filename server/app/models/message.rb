#--
#            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
#                    Version 2, December 2004
#
#            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
#   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
#
#  0. You just DO WHAT THE FUCK YOU WANT TO.
#++

class Message
  include DataMapper::Resource

  property :id,         Serial
  property :from,       String
  property :to,         String
  property :text,       Text
            
  property :created_at, DateTime  
  property :updated_at, DateTime

  before :save, :purge
  def purge
    self.text = Rack::Utils.escape_html self.text
  end

  class << self
    def send!(from, to, message)
      Message.create from: from, to: to, text: message
    end

    def fetch!(*users)
      p users
      Message.all(from: users.first, to: users.last ) |
      Message.all(from: users.last,  to: users.first)
    end
  end
end