note
	description: "Handles the rendering of game objects."
	author: "Raoul Wograndl"

class
	RENDERER

create
	make

feature

	make
	do
	end

	render(world: WORLD; current_round, current_score: INTEGER)
			-- Renders the current game state.
	do
		world.render

		print("%N[Round " + current_round.out + ", Score " + current_score.out + "]: ")
	end

end -- RENDERER
