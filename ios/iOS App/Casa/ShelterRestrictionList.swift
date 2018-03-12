//
//  ShelterRestrictionList.swift
//  
//
//  Created by Jared Duncan on 3/12/18.
//

class ShelterRestrictionList {
    var families: Bool
    var children: Bool
    var youngAdults: Bool
    var adults: Bool
    var veteransOnly: Bool
    var male: Bool
    var female: Bool
    
    init(families: Bool, children: Bool, youngAdults: Bool, adults: Bool, veteransOnly: Bool, male: Bool, female: Bool) {
        self.families = families
        self.children = children
        self.youngAdults = youngAdults
        self.adults = adults
        self.veteransOnly = veteransOnly
        self.male = male
        self.female = female
    }
    
    convenience init(restrictionsString: String) {
        let restrictionsString = restrictionsString.lowercased()
        if (restrictionsString.range(of: "anyone") != nil) {
            self.init(
                families: true,
                children: true,
                youngAdults: true,
                adults: true,
                veteransOnly: false,
                male: true,
                female: true
            )
        } else {
            self.init(
                families: restrictionsString.range(of: "families") != nil,
                children: restrictionsString.range(of: "children") != nil,
                youngAdults: restrictionsString.range(of: "young adults") != nil,
                adults: false,
                veteransOnly: restrictionsString.range(of: "veteran") != nil,
                male: restrictionsString.range(of: "men") != nil && restrictionsString.range(of: "women") == nil, // men is a substring of women
                female: restrictionsString.range(of: "women") != nil
            )
            // Set both genders to true if neither were in the string
            if (!self.male && !self.female) {
                self.male = true
                self.female = true
            }
            // Set adults to true if children/young adults not specified
            if (!self.families && !self.children && !self.youngAdults) {
                self.families = true
                self.children = true
                self.youngAdults = true
                self.adults = true
            }
        }
    }
    
    convenience init() {
        self.init(families: true, children: true, youngAdults: true, adults: true, veteransOnly: false, male: true, female: true)
    }
}
