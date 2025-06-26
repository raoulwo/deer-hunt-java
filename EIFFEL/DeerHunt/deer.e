note
	description: "A deer that the player needs to hunt."
	author: "Raoul Wograndl"

class
	DEER

inherit
	GAME_OBJECT

create
	default_make

feature
	default_make (starting_x, starting_y: INTEGER)
	do
		make ("X", "DEER", "DOWN", starting_x, starting_y)
	end
end
