note
	description: "Generic class of mapobjects"
	author: "MichaelWintersperger"

class
	MAP_OBJECT

create
    make

feature
	type: STRING --Type of the object
	symbol: STRING_32 --symbol representing the object on the map. is string for unicode support
	positionX: INTEGER -- Horizontal position on the board
	positionY: INTEGER --Vertical position on the board

    make (a_type: STRING; a_symbol: STRING_32; x, y: INTEGER)
        do
            type := a_type
            symbol := a_symbol
            positionX := x
            positionY := y
        end

feature --access

	returnType: STRING -- returns the type of the object
		do
			Result:= type
		end

	returnSymbol: STRING_32 -- returns the type of the object
		do
			Result:= symbol
		end

	returnX: INTEGER -- returns the X coordinate of the object
		do
			Result:= positionX
		end

	returnY: INTEGER -- returns the Y coordinate of the object
		do
			Result:= positionY
		end

feature -- set
	setSymbol(a_Symbol: STRING_32) -- set the symbol of the object
		do
			symbol:= a_symbol
		end

	setCoordinates(x,y : INTEGER) -- set the coordiantes of the object
		do
			positionX := x
			positionY := y
		end
end
