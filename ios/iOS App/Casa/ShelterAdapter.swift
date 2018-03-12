//
//  ShelterCollection.swift
//  
//
//  Created by Jared Duncan on 3/12/18.
//

class ShelterAdapter {
    private static var shelter_list = DatabaseInterfacer.getShelters()
    
    public static func getAllShelters() -> [Shelter] {
        return shelter_list;
    }
}
