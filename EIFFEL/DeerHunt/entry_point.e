note
	description: "The entry point for the game application."
	author: "Raoul Wograndl"

class
	ENTRY_POINT

create
	make

feature {NONE}

	make
			-- Entry point for the game application.
	local
		game: GAME
	do
		create game.make

		if game /= Void then
			game.start
		else
			io.put_string ("ERROR: Failed to instantiate game.%N")
		end
	end

end -- ENTRY_POINT
