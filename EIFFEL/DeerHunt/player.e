note
	description: "The player Mapobject"
	author: "MichaelWintersperger"

class
	PLAYER

inherit
	MAPOBJECT

create
    default_make

feature -- Initialization

    default_make (x, y: INTEGER)	--Set the type, symbol and coordinates
        do
            make ("Player", "→", x, y)
        end

end
