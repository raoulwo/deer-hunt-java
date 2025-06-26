note
	description: "Base class for all game objects."
	author: "Raoul Wograndl"

class
	GAME_OBJECT

create
	make

feature

	symbol: STRING
	type: STRING
	direction: STRING
	x: INTEGER
	y: INTEGER

	make (obj_symbol, obj_type, obj_direction: STRING; starting_x, starting_y: INTEGER)
	do
		symbol := obj_symbol
		type := obj_type
		direction := obj_direction
		x := starting_x
		y := starting_y
	end

	-- Getters and Setters

	get_symbol: STRING
	do
		Result := symbol
	end

	get_type: STRING
	do
		Result := type
	end

	get_direction: STRING
	do
		Result := direction
	end

	set_direction(new_direction: STRING)
	do
		direction := new_direction
	end

	get_x: INTEGER
	do
		Result := x
	end

	get_y: INTEGER
	do
		Result := y
	end

	set_position(new_x, new_y: INTEGER)
	do
		x := new_x
		y := new_y
	end

end -- GAME_OBJECT
