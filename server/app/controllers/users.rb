#--
#            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
#                    Version 2, December 2004
#
#            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
#   TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
#
#  0. You just DO WHAT THE FUCK YOU WANT TO.
#++

class Galax
  post '/user/login' do
    if not fields? :username, :password
      status = Status::EMPTY_REQUIRED_FIELD
    elsif logged_in?
      status = Status::ALREADY_LOGGED_IN
    else
      session  = User.login params[:username], params[:password]
      if session
        set_login! session
        status = Status::SUCCESS
      else
        status = Status::FAIL
      end
    end
    export status
  end
	
  get '/user/logout' do
    if logged_in?
      current_user.logout!
      delete_login!
      status = Status::SUCCESS
    else
      status = Status::NOT_LOGGED_IN
    end
    export status
  end
  
  get '/user/logged_in' do
    export logged_in? ? Status::SUCCESS : Status::FAIL
  end

  post '/user/signup' do
    if not fields? :username, :email, :password
      status = Status::EMPTY_REQUIRED_FIELD
    elsif logged_in?
      status = Status::ALREADY_LOGGED_IN
    elsif User.exists? params[:username]
      status = Status::DENIED
    else
      level = User.empty? ? User.founder : User.user
      user  = User.signup params[:username], params[:email], params[:password], level
      if user.errors.any?
        status = { status: Status::FAIL, error: user.errors.first.first }
      else
        status = Status::SUCCESS
      end
    end
    export status
  end
  
  post '/user/message/:to' do |to|
    if    not fields? :text
      status = Status::EMPTY_REQUIRED_FIELD
    elsif not logged_in?
      status = Status::NOT_LOGGED_IN
    elsif     current_user.username == to
      status = Status::DENIED
    elsif not User.exists? to
      status = Status::NOT_FOUND
    else
      message = Message.send! current_user.username, to, params[:text]
      if message.errors.any?
        status = { status: Status::FAIL, error: message.errors.first.first }
      else
        status = Status::SUCCESS
      end
    end
    export status
  end
  
  get '/user/messages/:user' do |user|
    if not logged_in?
      status = Status::NOT_LOGGED_IN
    elsif current_user.username == user
      status = Status::DENIED
    else
      return export Message.fetch!(current_user.username, user)
    end
    export status
  end
end
