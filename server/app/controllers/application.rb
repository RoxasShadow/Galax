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
  get '/get_address' do
    return export Status::NOT_LOGGED_IN unless logged_in?
    
    export address: current_user.address
  end
	
  post '/set_address' do
    return export Status::NOT_LOGGED_IN unless logged_in?

    update = current_user.update(address: params[:address])
    export status: update ? Status::SUCCESS : Status::FAIL
  end

  get '/people' do
    return export Status::NOT_LOGGED_IN unless logged_in?
    return export Status::FAIL       if     current_user.address.strip.empty?

    export current_user.find_people, only: [:id, :username, :email, :address]
  end

  # bisogna cancellarli 'sti due?
  
  post '/send/:recipient' do |recipient|
    return export Status::EMPTY_REQUIRED_FIELD unless params[:text]
    return export Status::NOT_LOGGED_IN           unless logged_in?
    return export Status::NOT_FOUND            unless User.exists? recipient

    message = current_user.messages.create recipient: recipient, text: params[:text]
    export message.errors.any? ? { status: Status::FAIL, error: message.errors.first.first } : Status::SUCCESS
  end
  
  get '/get/:recipient' do |recipient|
    return export Status::NOT_LOGGED_IN  unless logged_in?
    return export Status::NOT_FOUND   unless User.exists? recipient

    export current_user.messages.all recipient: recipient
  end
end