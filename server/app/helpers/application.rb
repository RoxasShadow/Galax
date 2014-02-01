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
	helpers do
    def fields?(*args)
      args.each { |a|
        return false unless params.include? a.to_s
      }
      true
    end

    def export(collection, options = {}, callback = nil)
      content_type :json
      
      only    = options.include?(:only)    ? options[:only   ] : []
      exclude = options.include?(:exclude) ? options[:exclude] : []
      methods = options.include?(:methods) ? options[:methods] : []

      collection = { status: collection } unless collection.is_a? Enumerable
      collection.to_json only: only, exclude: exclude, methods: methods
    end
	end
end
