note
	description: "The projectile game object."
	author: "Raoul Wograndl"

class
	PROJECTILE

inherit
	GAME_OBJECT

create
	default_make

feature

	default_make (proj_direction: STRING; starting_x, starting_y: INTEGER)
	do
		make ("+", "PROJECTILE", proj_direction, starting_x, starting_y)
	end

end
