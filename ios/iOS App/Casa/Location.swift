//
//  Location.swift
//  
//
//  Created by Jared Duncan on 3/12/18.
//

class Location {
    var latitude: Double
    var longitude: Double
    var address: String
    
    init(latitude: Double, longitude: Double, address: String) {
        self.latitude = latitude
        self.longitude = longitude
        self.address = address
    }
}
