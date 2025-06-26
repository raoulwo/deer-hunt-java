note
	description: "Represents a single world tile."
	author: "Raoul Wograndl"

class
	TILE

inherit
	GAME_OBJECT

create
	default_make

feature
	default_make(starting_x, starting_y: INTEGER)
	do
		make (".", "TILE", "DOWN", starting_x, starting_y)
	end

end -- TILE
