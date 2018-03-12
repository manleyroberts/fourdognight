//
//  ShelterRestrictionList.swift
//  
//
//  Created by Jared Duncan on 3/12/18.
//

class ShelterRestrictionList {
    var children: Bool
    var youngAdults: Bool
    var adults: Bool
    var familiesOnly: Bool
    var veteransOnly: Bool
    var male: Bool
    var female: Bool
    
    init(children: Bool, youngAdults: Bool, adults: Bool, familiesOnly: Bool, veteransOnly: Bool, male: Bool, female: Bool) {
        self.children = children
        self.youngAdults = youngAdults
        self.adults = adults
        self.familiesOnly = familiesOnly
        self.veteransOnly = veteransOnly
        self.male = male
        self.female = female
    }
}
