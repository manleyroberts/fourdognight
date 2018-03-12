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
    var restrictionsString: String
    weak var restrictions: ShelterRestrictionList?
    
    init(id: Int, name: String, capacity: Int, notes: String, phone: String, location: Location, restrictionsString: String) {
        self.id = id
        self.name = name
        self.capacity = capacity
        self.notes = notes
        self.phone = phone
        self.location = location
        self.restrictionsString = restrictionsString
        self.restrictions = processRestrictions()
    }
    
    private func processRestrictions() -> ShelterRestrictionList {
        var restrictions: ShelterRestrictionList
        if (restrictionsString.range(of: "anyone") != nil) {
            restrictions = ShelterRestrictionList(
                children: true,
                youngAdults: true,
                adults: true,
                familiesOnly: true,
                veteransOnly: true,
                male: true,
                female: true
            )
        } else {
            restrictions = ShelterRestrictionList(
                children: restrictionsString.range(of: "children") != nil,
                youngAdults: restrictionsString.range(of: "young adults") != nil,
                adults: false,
                familiesOnly: restrictionsString.range(of: "families") != nil,
                veteransOnly: restrictionsString.range(of: "veteran") != nil,
                male: restrictionsString.range(of: "men") != nil && restrictionsString.range(of: "women") == nil, // men is a substring of women
                female: restrictionsString.range(of: "women") != nil
            )
            // Set both genders to true if neither were in the string
            if (!restrictions.male && !restrictions.female) {
                restrictions.male = true
                restrictions.female = true
            }
            // Set adults to true if children/young adults not specified
            if ((!restrictions.children && !restrictions.youngAdults) || restrictions.familiesOnly) {
                restrictions.children = true
                restrictions.youngAdults = true
                restrictions.adults = true
            }
        }
        return restrictions
    }
}
