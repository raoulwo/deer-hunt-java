note
	description: "An obstacle characters can't move through."
	author: "Raoul Wograndl"

class
	OBSTACLE

inherit
	GAME_OBJECT

create
	default_make

feature

	default_make (starting_x, starting_y: INTEGER)
	do
		make ("O", "OBSTACLE", "DOWN", starting_x, starting_y)
	end

end -- OBSTACLE
