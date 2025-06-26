note
	description: "The player game object."
	author: "Michael Wintersperger"

class
	PLAYER

inherit
	GAME_OBJECT
	redefine
		get_symbol
	end

create
    default_make

feature

    default_make (starting_x, starting_y: INTEGER)
    do
        make ("v", "PLAYER", "DOWN", starting_x, starting_y)
    end

    get_symbol: STRING
    do
    	if get_direction.is_equal ("UP") then
    		Result := "^"
    	elseif get_direction.is_equal ("DOWN") then
    		Result := "v"
       	elseif get_direction.is_equal ("LEFT") then
    		Result := "<"
    	else
    		Result := ">"
    	end
    end

end -- PLAYER
