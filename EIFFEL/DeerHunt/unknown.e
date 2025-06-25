note
	description: "The unknon Mapobject"
	author: "MichaelWintersperger"

class
	UNKNOWN

inherit
	MAPOBJECT

create
    default_make

feature -- Initialization

    default_make (x, y: INTEGER)	--Set the type, symbol and coordinates
        do
            make ("Unknown", "X", x, y)
        end

end
