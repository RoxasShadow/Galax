#--
#            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
#                    Version 2, December 2004
#
#            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
#   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
#
#  0. You just DO WHAT THE FUCK YOU WANT TO.
#++

require 'sinatra/base'
require 'bcrypt'
require 'json'
require 'data_mapper'
require 'dm-sqlite-adapter'

class Galax < Sinatra::Base
  db_path = File.join Dir.pwd, '../', 'db'
  Dir.mkdir db_path unless Dir.exists? db_path
  DataMapper.setup :default, "sqlite3://#{db_path}/test.db"
  
  configure {
    use Rack::Session::Cookie,
      :path   => ?/,
      :secret => 'VKfR5pkrjfmklrjmmrjkjfrkjnfLkjrncEuap'
    }
  
  Dir.glob("../app/helpers/*.rb")     { |m| require m.chomp }
  Dir.glob("../app/models/*.rb")      { |m| require m.chomp }
  Dir.glob("../app/controllers/*.rb") { |m| require m.chomp }

  DataMapper.finalize
  DataMapper.auto_upgrade!
end
