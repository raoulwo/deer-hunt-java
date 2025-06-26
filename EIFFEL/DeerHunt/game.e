note
	description: "The core game class containing the game logic."
	author: "Raoul Wograndl"

class
	GAME

create
	make

feature

	renderer: RENDERER

	current_score: INTEGER
	current_round: INTEGER
	player_input: STRING
	game_over: BOOLEAN

	make
	do
		current_score := 0
		current_round := 0
		player_input := ""
		game_over := FALSE

		create renderer.make
	end

	start
			-- Start the game loop.
	local
		world: WORLD
	do
		create world.make(Current)

		from
		until
			game_over
		loop
			-- Render the game world.
			renderer.render(world, current_round, current_score)

			-- Read the player input.
			player_input := read_input

			-- Update the game state.
			update(world)

			-- Increment the current round.
			current_round := current_round + 1
		end

		print("%NGame Over! Final Score: " + current_score.out + "%N")
	end

	get_score: INTEGER
	do
		Result := current_score
	end

	set_score(new_score: INTEGER)
	do
		current_score := new_score
	end

feature {NONE}

	read_input: STRING
			-- Reads a line from standard input and returns it as a lowercase string.
	do
		io.read_line
		Result := io.last_string.as_lower
	end

	update(world: WORLD)
			-- Updates the game state based on the player input.
	do
		world.update

		if player_input.is_equal ("stop") then
			game_over := TRUE
		elseif current_round >= 100 then
			game_over := TRUE
		elseif player_input.is_equal ("up") then
			world.move_character_up(world.get_player)
		elseif player_input.is_equal ("down") then
			world.move_character_down(world.get_player)
		elseif player_input.is_equal ("left") then
			world.move_character_left(world.get_player)
		elseif player_input.is_equal ("right") then
			world.move_character_right(world.get_player)
		elseif player_input.is_equal ("shoot") then
			world.shoot
		end
	end

end -- GAME
