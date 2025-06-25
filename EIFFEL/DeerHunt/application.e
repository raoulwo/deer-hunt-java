note
	description: "DeerHunt application root class"
	author: "Michael Wintersperger"

class
	APPLICATION

inherit
	ARGUMENTS_32

create
	make

feature {NONE} -- Initialization

	make -- Run application.
		local
			map: ARRAY [ARRAY [MAPOBJECT]] -- two dimensional array storing the gamestate
			player: PLAYER -- Player character
			input: STRING -- input variable
			gameOver: BOOLEAN -- Boolean variable for the game loop
			i: INTEGER -- y coordinate
			j: INTEGER -- x coordinate
		do
			create map.make_filled (create {ARRAY [MAPOBJECT]}.make_filled (create {FIELD}.default_make (0, 0), 1, 80), 1, 40) -- Create the game space 80 spaces wide, 40 spaces tall

				--Iterate over the entire map and create new instances because make_filled creates references to the same object, instead of different ones.
			from
				i := map.lower
			until
				i > map.upper
			loop
				map.put (create {ARRAY [MAPOBJECT]}.make_filled (create {FIELD}.default_make (0, 0), 1, 80), i)
				from
					j := map [i].lower
				until
					j > map [i].upper
				loop
					map [i].put (create {FIELD}.default_make (j, i), j)
					j := j + 1
				end
				i := i + 1
			end

			create player.default_make (5, 5) -- initialize and then place the player
			map[5].put (player, 5)

			from
				gameOver := FALSE
			until
				gameOver = TRUE
			loop
				i:=player.returnX -- reuse these variables as temp store of coordiantes
				j:=player.returnY
				-- Print the contents of the array
				print_map (i, j, map)
				io.read_line
				input := io.last_string.as_lower
				if input.is_equal ("stop") then -- Exit Game on string "stop"
					gameOver := TRUE
				elseif input.is_equal ("left") then -- move the player character to the left on "left" if it is possible
					if check_collision(i-1, j,map) = FALSE then
						map[j].put (player, i-1)
						map[j].put (create {FIELD}.default_make (i, j), i)
					end
				end
			end
		end

feature -- Utility
	-- check if there is already something other than a FIELD MAPOBJECT on the coordinates a,b, and that the coordinates a,b are within bounds of arr
	check_collision(a, b: INTEGER; arr: attached ARRAY [ARRAY [MAPOBJECT]]): BOOLEAN -- returns true if there is collision, false if there isn't
	do
		if b > arr.upper or b < arr.lower then -- b is y coordinates
			print("y")
			RESULT:= TRUE
		end
		if a > arr[1].upper or a < arr[1].lower then -- a is x coordinates
			print("x")
			RESULT:= TRUE
		end
		if arr[b][a].returnType.is_equal("FIELD") then
			RESULT:= FALSE
		end
		RESULT:= TRUE
	end

		--hide parts of the map not visible to the player
	set_unknowns (a: INTEGER; b: INTEGER; arr: attached ARRAY [ARRAY [MAPOBJECT]]): ARRAY [ARRAY [MAPOBJECT]] -- a and b are the x and y coordinates of the player respectively
		local
			visibleMap: ARRAY [ARRAY [MAPOBJECT]] -- two dimensional array to be shown
			i: INTEGER -- y coordinate
			j: INTEGER -- x coordinate
		do
				-- Create a new array with same bounds
			create visibleMap.make_filled (create {ARRAY [MAPOBJECT]}.make_filled (create {UNKNOWN}.default_make (0, 0), 1, 80), 1, 40) -- Create the game space 80 spaces wide, 40 spaces tall
				--iterate over it like before
			from
				i := visibleMap.lower
			until
				i > visibleMap.upper
			loop
				visibleMap.put (create {ARRAY [MAPOBJECT]}.make_filled (create {UNKNOWN}.default_make (0, 0), 1, 80), i)
				from
					j := visibleMap [i].lower
				until
					j > visibleMap [i].upper
				loop
					visibleMap [i].put (create {UNKNOWN}.default_make (j, i), j)
					j := j + 1
				end
				i := i + 1
			end

				-- reveal the map within five spaces around the player
			from
				i := visibleMap.lower
			until
				i > visibleMap.upper
			loop
				from
					j := visibleMap [i].lower
				until
					j > visibleMap [i].upper
				loop
					if (a - j).abs <= 5 and (b - i).abs <= 5 then
						visibleMap [i].put (arr [i] [j], j)
					end
					j := j + 1
				end
				io.put_new_line
				i := i + 1
			end

			--Add a viewcone

			Result := visibleMap
		end

		--print the current map on the CLI
	print_map (a: INTEGER; b: INTEGER; arr: attached ARRAY [ARRAY [MAPOBJECT]]) -- a and b are the x and y coordinates of the player respectively
		local
			visibleMap: ARRAY [ARRAY [MAPOBJECT]] -- two dimensional array to be shown
			i: INTEGER -- y coordinate
			j: INTEGER -- x coordinate
		do
			visibleMap := set_unknowns (a, b, arr)
				-- Clean the prior gamestate
			io.put_new_line
			io.put_new_line
			io.put_new_line
			io.put_new_line
			io.put_new_line
			io.put_new_line
			io.put_new_line
				-- Print each element of the array
			from
				i := visibleMap.lower
			until
				i > visibleMap.upper
			loop
				from
					j := visibleMap [i].lower
				until
					j > visibleMap [i].upper
				loop
					print (visibleMap [i] [j].returnSymbol)
					j := j + 1
				end
				io.put_new_line
				i := i + 1
			end
			io.put_new_line
		end

end
