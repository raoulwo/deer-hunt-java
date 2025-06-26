note
	description: "The game world containing all game objects."
	author: "Raoul Wograndl"

class
	WORLD

create
	make

feature

	game: GAME

	max_world_rows: INTEGER = 15
	max_world_cols: INTEGER = 25

	random_generator: RANDOM

	-- Two-dimensional array of game objects.
	map: ARRAY [ARRAY [GAME_OBJECT]]

	player: PLAYER

	make (game_obj: GAME)
	local
		i: INTEGER
		j: INTEGER
	do
		game := game_obj

		create random_generator.set_seed(1234567890)

		-- Instantiate the game map.
		create map.make_filled (create {ARRAY [GAME_OBJECT]}.make_filled (create {TILE}.default_make (0, 0), 1, max_world_cols), 1, max_world_rows)

		-- Iterate over the entire map and create new instances because make_filled creates references to the same object, instead of different ones.
		from
			i := map.lower
		until
			i > map.upper
		loop
			map.put (create {ARRAY [GAME_OBJECT]}.make_filled (create {TILE}.default_make (0, 0), 1, max_world_cols), i)
			from
				j := map [i].lower
			until
				j > map [i].upper
			loop
				map [i].put (create {TILE}.default_make (j, i), j)
				j := j + 1
			end
			i := i + 1
		end

		-- Instantiate the player.
		create player.default_make(13, 8)
		map[8].put (create {PLAYER}.default_make (13, 8), 13)

		-- Instantiate deer.
		map[2].put (create {DEER}.default_make (2, 2), 2)
		map[2].put (create {DEER}.default_make (24, 2), 24)
		map[14].put (create {DEER}.default_make (2, 14), 2)
		map[14].put (create {DEER}.default_make (24, 14), 24)

		map[8].put (create {DEER}.default_make (2, 8), 2)
		map[8].put (create {DEER}.default_make (24, 8), 24)
		map[2].put (create {DEER}.default_make (13, 2), 13)
		map[14].put (create {DEER}.default_make (13, 14), 13)

		-- Instantiate obstacles.
		map[5].put (create {OBSTACLE}.default_make (13, 5), 13)
		map[8].put (create {OBSTACLE}.default_make (10, 8), 10)
		map[8].put (create {OBSTACLE}.default_make (16, 8), 16)
		map[11].put (create {OBSTACLE}.default_make (13, 11), 13)

		map [4].put (create {OBSTACLE}.default_make (4, 4), 4)
		map [4].put (create {OBSTACLE}.default_make (21, 4), 21)
		map [12].put (create {OBSTACLE}.default_make (4, 12), 4)
		map [12].put (create {OBSTACLE}.default_make (21, 12), 21)
	end

	render
			-- Renders the game world.
	local
		row: INTEGER
		col: INTEGER
		output: STRING
	do
		output := ""

		from
			row := map.lower
		until
			row > map.upper
		loop

			from
				col := map [row].lower
			until
				col > map [row].upper
			loop
				output.append(map [row][col].get_symbol)

				col := col + 1
			end
			output.append("%N")

			row := row + 1
		end

		print(output)
	end

	get_player: GAME_OBJECT
	do
		Result := player
	end

	update
			-- Updates the projectiles and deer.
	local
		row: INTEGER
		col: INTEGER
		curr_game_obj: GAME_OBJECT
	do
		from
			row := map.lower
		until
			row > map.upper
		loop

			from
				col := map [row].lower
			until
				col > map [row].upper
			loop
				curr_game_obj := map [row][col]

				if curr_game_obj.get_type.is_equal ("PROJECTILE") then
					update_projectile(curr_game_obj)
				elseif curr_game_obj.get_type.is_equal ("DEER") then
					update_deer(curr_game_obj)
				end

				col := col + 1
			end


			row := row + 1
		end
	end

	update_projectile(projectile: GAME_OBJECT)
	do
		if projectile.direction.is_equal ("UP") then
			move_projectile_up(projectile)
		elseif projectile.direction.is_equal ("DOWN") then
			move_projectile_down(projectile)
		elseif projectile.direction.is_equal ("LEFT") then
			move_projectile_left(projectile)
		else
			move_projectile_right(projectile)
		end
	end

	update_deer(deer: GAME_OBJECT)
	local
		next_direction: STRING
	do
		next_direction := get_random_direction
		if next_direction.is_equal ("UP") then
			move_character_up(deer)
		elseif next_direction.is_equal ("DOWN") then
			move_character_down(deer)
		elseif next_direction.is_equal ("LEFT") then
			move_character_left(deer)
		else
			move_character_right(deer)
		end
	end

	check_collision(x, y: INTEGER; arr: attached ARRAY [ARRAY [GAME_OBJECT]]): BOOLEAN
			-- Check if there is already something other than a TILE on the coordinates x,y and that the coordinates x,y are within the bounds of arr.
			-- Returns true if there was a collision, false if there wasn't.
	do
		if check_out_of_bounds(x, y, arr) then
			RESULT:= TRUE
		elseif arr[y][x].get_type.is_equal("TILE") then
			RESULT:= FALSE
		else
			RESULT:= TRUE
		end
	end

	check_out_of_bounds(x, y: INTEGER; arr: attached ARRAY [ARRAY [GAME_OBJECT]]): BOOLEAN
	do
		RESULT := FALSE
		if y > arr.upper or y < arr.lower then
			RESULT:= TRUE
		end
		if x > arr[1].upper or x < arr[1].lower then
			RESULT:= TRUE
		end
	end

	check_deer_collision(x, y: INTEGER; arr: attached ARRAY [ARRAY [GAME_OBJECT]]): BOOLEAN
	do
		if arr[y][x].get_type.is_equal("DEER") then
			RESULT:= TRUE
		else
			RESULT:= FALSE
		end
	end

	move_character_up(char: GAME_OBJECT)
	local
		next_x: INTEGER
		next_y: INTEGER
	do
		next_x := char.get_x
		next_y := char.get_y - 1

		if check_collision(next_x, next_y, map) = TRUE then
			print("Collision moving up%N")
		else
			move_game_obj_up(char)
		end
	end

	move_character_down(char: GAME_OBJECT)
	local
		next_x: INTEGER
		next_y: INTEGER
	do
		next_x := char.get_x
		next_y := char.get_y + 1

		if check_collision(next_x, next_y, map) = TRUE then
			print("Collision moving down%N")
		else
			move_game_obj_down(char)
		end
	end

	move_character_left(char: GAME_OBJECT)
	local
		next_x: INTEGER
		next_y: INTEGER
	do
		next_x := char.get_x - 1
		next_y := char.get_y

		if check_collision(next_x, next_y, map) = TRUE then
			print("Collision moving left%N")
		else
			move_game_obj_left(char)
		end
	end

	move_character_right(char: GAME_OBJECT)
	local
		next_x: INTEGER
		next_y: INTEGER
	do
		next_x := char.get_x + 1
		next_y := char.get_y

		if check_collision(next_x, next_y, map) = TRUE then
			print("Collision moving right%N")
		else
			move_game_obj_right(char)
		end
	end

	move_projectile_up(projectile: GAME_OBJECT)
	local
		curr_x: INTEGER
		curr_y: INTEGER
		new_x: INTEGER
		new_y: INTEGER
	do
		curr_x := projectile.get_x
		curr_y := projectile.get_y
		new_x := curr_x
		new_y := curr_y - 1

		if check_out_of_bounds(new_x, new_y, map) then
			-- Remove projectile on collision.
			map [curr_y].put (create {TILE}.default_make (curr_x, curr_y), curr_x)
		elseif check_deer_collision(new_x, new_y, map) then
			game.set_score(game.get_score + 5)
			-- Remove projectile on collision.
			map [curr_y].put (create {TILE}.default_make (curr_x, curr_y), curr_x)
		elseif check_collision(new_x, new_y, map) then
			print("Projectile would collide.%N")
			-- Remove projectile on collision.
			map [curr_y].put (create {TILE}.default_make (curr_x, curr_y), curr_x)
		else
			move_game_obj_up(projectile)
		end
	end

	move_projectile_down(projectile: GAME_OBJECT)
	local
		curr_x: INTEGER
		curr_y: INTEGER
		new_x: INTEGER
		new_y: INTEGER
	do
		curr_x := projectile.get_x
		curr_y := projectile.get_y
		new_x := curr_x
		new_y := curr_y + 1

		if check_out_of_bounds(new_x, new_y, map) then
			-- Remove projectile on collision.
			map [curr_y].put (create {TILE}.default_make (curr_x, curr_y), curr_x)
		elseif check_deer_collision(new_x, new_y, map) then
			game.set_score(game.get_score + 5)
			-- Remove projectile on collision.
			map [curr_y].put (create {TILE}.default_make (curr_x, curr_y), curr_x)
		elseif check_collision(new_x, new_y, map) then
			print("Projectile would collide.%N")
			-- Remove projectile on collision.
			map [curr_y].put (create {TILE}.default_make (curr_x, curr_y), curr_x)
		else
			move_game_obj_down(projectile)
		end
	end

	move_projectile_left(projectile: GAME_OBJECT)
	local
		curr_x: INTEGER
		curr_y: INTEGER
		new_x: INTEGER
		new_y: INTEGER
	do
		curr_x := projectile.get_x
		curr_y := projectile.get_y
		new_x := curr_x - 1
		new_y := curr_y

		if check_out_of_bounds(new_x, new_y, map) then
			-- Remove projectile on collision.
			map [curr_y].put (create {TILE}.default_make (curr_x, curr_y), curr_x)
		elseif check_deer_collision(new_x, new_y, map) then
			game.set_score(game.get_score + 5)
			-- Remove projectile on collision.
			map [curr_y].put (create {TILE}.default_make (curr_x, curr_y), curr_x)
		elseif check_collision(new_x, new_y, map) then
			print("Projectile would collide.%N")
			-- Remove projectile on collision.
			map [curr_y].put (create {TILE}.default_make (curr_x, curr_y), curr_x)
		else
			move_game_obj_left(projectile)
		end
	end

	move_projectile_right(projectile: GAME_OBJECT)
	local
		curr_x: INTEGER
		curr_y: INTEGER
		new_x: INTEGER
		new_y: INTEGER
	do
		curr_x := projectile.get_x
		curr_y := projectile.get_y
		new_x := curr_x + 1
		new_y := curr_y

		if check_out_of_bounds(new_x, new_y, map) then
			-- Remove projectile on collision.
			map [curr_y].put (create {TILE}.default_make (curr_x, curr_y), curr_x)
		elseif check_deer_collision(new_x, new_y, map) then
			game.set_score(game.get_score + 5)
			-- Remove projectile on collision.
			map [curr_y].put (create {TILE}.default_make (curr_x, curr_y), curr_x)
		elseif check_collision(new_x, new_y, map) then
			print("Projectile would collide.%N")
			-- Remove projectile on collision.
			map [curr_y].put (create {TILE}.default_make (curr_x, curr_y), curr_x)
		else
			move_game_obj_right(projectile)
		end
	end

	move_game_obj_up(game_obj: GAME_OBJECT)
	local
		curr_x: INTEGER
		curr_y: INTEGER
		new_x: INTEGER
		new_y: INTEGER
	do
		curr_x := game_obj.get_x
		curr_y := game_obj.get_y
		new_x := curr_x
		new_y := curr_y - 1
		game_obj.set_direction ("UP")
		game_obj.set_position (new_x, new_y)
		map [new_y].put (game_obj, new_x)
		map [curr_y].put (create {TILE}.default_make (curr_x, curr_y), curr_x)
	end

	move_game_obj_down(game_obj: GAME_OBJECT)
	local
		curr_x: INTEGER
		curr_y: INTEGER
		new_x: INTEGER
		new_y: INTEGER
	do
		curr_x := game_obj.get_x
		curr_y := game_obj.get_y
		new_x := curr_x
		new_y := curr_y + 1
		game_obj.set_direction ("DOWN")
		game_obj.set_position (new_x, new_y)
		map [new_y].put (game_obj, new_x)
		map [curr_y].put (create {TILE}.default_make (curr_x, curr_y), curr_x)
	end

	move_game_obj_left(game_obj: GAME_OBJECT)
	local
		curr_x: INTEGER
		curr_y: INTEGER
		new_x: INTEGER
		new_y: INTEGER
	do
		curr_x := game_obj.get_x
		curr_y := game_obj.get_y
		new_x := curr_x - 1
		new_y := curr_y
		game_obj.set_direction ("LEFT")
		game_obj.set_position (new_x, new_y)
		map [new_y].put (game_obj, new_x)
		map [curr_y].put (create {TILE}.default_make (curr_x, curr_y), curr_x)
	end

	move_game_obj_right(game_obj: GAME_OBJECT)
	local
		curr_x: INTEGER
		curr_y: INTEGER
		new_x: INTEGER
		new_y: INTEGER
	do
		curr_x := game_obj.get_x
		curr_y := game_obj.get_y
		new_x := curr_x + 1
		new_y := curr_y
		game_obj.set_direction ("RIGHT")
		game_obj.set_position (new_x, new_y)
		map [new_y].put (game_obj, new_x)
		map [curr_y].put (create {TILE}.default_make (curr_x, curr_y), curr_x)
	end

	get_random_direction: STRING
	local
		random_num: INTEGER
	do
		random_generator.forth
		random_num := random_generator.item \\ 4 + 1
		if random_num = 1 then
			Result := "UP"
		elseif random_num = 2 then
			Result := "DOWN"
		elseif random_num = 3 then
			Result := "LEFT"
		else
			Result := "RIGHT"
		end
	end

	shoot
	do
		if player.get_direction.is_equal ("UP") then
			shoot_up
		elseif player.get_direction.is_equal ("DOWN") then
			shoot_down
		elseif player.get_direction.is_equal ("LEFT") then
			shoot_left
		else
			shoot_right
		end
	end

	shoot_up
	local
		target_x: INTEGER
		target_y: INTEGER
	do
		target_x := player.get_x
		target_y := player.get_y - 1

		if check_out_of_bounds(target_x, target_y, map) then
			print("Projectile would be out of bounds.%N")
		elseif check_deer_collision(target_x, target_y, map) then
			game.set_score(game.get_score + 5)
		elseif check_collision(target_x, target_y, map) = False then
			-- Create a projectile at target position.
			map [target_y].put (create {PROJECTILE}.default_make ("UP", target_x, target_y), target_x)
		end
	end

	shoot_down
	local
		target_x: INTEGER
		target_y: INTEGER
	do
		target_x := player.get_x
		target_y := player.get_y + 1

		if check_out_of_bounds(target_x, target_y, map) then
			print("Projectile would be out of bounds.%N")
		elseif check_deer_collision(target_x, target_y, map) then
			game.set_score(game.get_score + 5)
		elseif check_collision(target_x, target_y, map) = False then
			-- Create a projectile at target position.
			map [target_y].put (create {PROJECTILE}.default_make ("DOWN", target_x, target_y), target_x)
		end
	end

	shoot_left
	local
		target_x: INTEGER
		target_y: INTEGER
	do
		target_x := player.get_x - 1
		target_y := player.get_y

		if check_out_of_bounds(target_x, target_y, map) then
			print("Projectile would be out of bounds.%N")
		elseif check_deer_collision(target_x, target_y, map) then
			game.set_score(game.get_score + 5)
		elseif check_collision(target_x, target_y, map) = False then
			-- Create a projectile at target position.
			map [target_y].put (create {PROJECTILE}.default_make ("LEFT", target_x, target_y), target_x)
		end
	end

	shoot_right
	local
		target_x: INTEGER
		target_y: INTEGER
	do
		target_x := player.get_x + 1
		target_y := player.get_y

		if check_out_of_bounds(target_x, target_y, map) then
			print("Projectile would be out of bounds.%N")
		elseif check_deer_collision(target_x, target_y, map) then
			game.set_score(game.get_score + 5)
		elseif check_collision(target_x, target_y, map) = False then
			-- Create a projectile at target position.
			map [target_y].put (create {PROJECTILE}.default_make ("RIGHT", target_x, target_y), target_x)
		end
	end
end
