ENV['RACK_ENV'] = 'test'

require './spec'
require 'rspec'
require 'rack/test'

describe 'Galax\'s APIs' do
  include Rack::Test::Methods

  def app
    @app ||= Galax.new
  end

  before do
    @users = [
      {
        :username => 'Roxas',
        :email    => 'roxas@shadow.rs',
        :password => 'roxas13',
        :level    => User.founder
      },

      {
        :username => 'Sora',
        :email    => 'sora@shadow.ss',
        :password => 'sora13',
        :level    => User.user
      },

      {
        :username => 'Riku',
        :email    => 'riku@shadow.rs',
        :password => 'riku13',
        :level    => User.user
      }
    ]
  end

  it 'creates users' do
    @users.each { |user|
      dat_user = User.signup user[:username], user[:email], user[:password], user[:level]
      dat_user.errors.should               be_empty

      User.exists?(user[:username]).should be_true

      User.login(user[:username], user[:password]).should_not be_false
    }
  end

  it 'allows an user to send messages to another user' do
    u1  = @users[0][:username]
    u2  = @users[1][:username]
    u3  = @users[2][:username]
    msg = 'we ciao '

    Message.send!(u1, u2, msg).errors.should be_empty

    Message.fetch!(u1, u2).first.text.should eql(msg)
    Message.fetch!(u1, u3).should            be_empty
  end
end
