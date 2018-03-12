//
//  Shelter.swift
//  Casa
//
//  Created by Jared Duncan on 3/9/18.
//  Copyright © 2018 Four Dog Night. All rights reserved.
//

class Shelter {
    var id: Int
    var name: String
    var capacity: Int
    var notes: String
    var phone: String
    var location: Location
    var restrictions: ShelterRestrictionList
    
    init(id: Int, name: String, capacity: Int, notes: String, phone: String, location: Location, restrictions: ShelterRestrictionList) {
        self.id = id
        self.name = name
        self.capacity = capacity
        self.notes = notes
        self.phone = phone
        self.location = location
        self.restrictions = restrictions
    }
}
