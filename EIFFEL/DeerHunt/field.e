note
	description: "The basic Mapobject"
	author: "MichaelWintersperger"

class
	FIELD

inherit
	MAPOBJECT

create
    default_make

feature -- Initialization

    default_make (x, y: INTEGER)	--Set the type, symbol and coordinates
        do
            make ("FIELD", ".", x, y)
        end

end
