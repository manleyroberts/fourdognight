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
    
    public static func getSheltersByAge(age: Age) -> [Shelter] {
        var output_list: [Shelter] = []
        // This could probably be reworked
        for shelter in shelter_list {
            switch age {
            case Age.Families:
                if shelter.restrictions.families {
                    output_list.append(shelter)
                }
            case Age.Children:
                if shelter.restrictions.children {
                    output_list.append(shelter)
                }
            case Age.YoungAdults:
                if shelter.restrictions.youngAdults {
                    output_list.append(shelter)
                }
            case Age.Adults:
                if shelter.restrictions.adults {
                    output_list.append(shelter)
                }
            }
        }
        return output_list
    }
    
    public static func getSheltersByGender(male: Bool) -> [Shelter] {
        var output_list: [Shelter] = []
        for shelter in shelter_list {
            if male && shelter.restrictions.male {
                output_list.append(shelter)
            } else if !male && shelter.restrictions.female {
                output_list.append(shelter)
            }
        }
        return output_list
    }
    
    public static func getSheltersByName(name: String) -> [Shelter] {
        var output_list: [Shelter] = []
        for shelter in shelter_list {
            if shelter.name == name {
                output_list.append(shelter)
            }
        }
        return output_list
    }
}
