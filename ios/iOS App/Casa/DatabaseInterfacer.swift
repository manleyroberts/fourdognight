//
//  DatabaseInterfacer.swift
//  Casa
//
//  Created by Jared Duncan on 3/9/18.
//  Copyright © 2018 Four Dog Night. All rights reserved.
//

import Foundation

class DatabaseInterfacer {
    static func getUsers() -> [String: AbstractUser] {
        return [:]
    }
    
    static func getShelters() -> [Shelter] {
        guard let path = Bundle.main.path(forResource: "homelessshelterdatabase", ofType: "csv") else {
            return []
        }
        var csvData: String
        do {
            csvData = try String(contentsOfFile: path, encoding: String.Encoding.utf8)
        } catch {
            return []
        }
        var rows: [String] = []
        csvData.enumerateLines { row, _ in
            rows.append(row)
        }
        let firstRow = rows.removeFirst()
        var shelters: [Shelter] = []
        var columns: [String: Int] = [:]
        let columnNames = firstRow.components(separatedBy: ",")
        for i in 0..<columnNames.count {
            columns[columnNames[i]] = i
        }
        for row in rows {
            do {
                let regex = try NSRegularExpression(pattern: "((?<=\")[^\"]*(?=\"(,|$)+)|(?<=,|^)[^,\"]*(?=,|$))")
                let results = regex.matches(in: row,
                                            range: NSRange(row.startIndex..., in: row))
                var columnData = results.map {
                    String(row[Range($0.range, in: row)!])
                }
                let restrictions = processRestrictions(restrictionsString: columnData[columns["Restrictions"]!].lowercased())
                let location = Location(
                    latitude: Double(columnData[columns["Latitude"]!])!,
                    longitude: Double(columnData[columns["Longitude"]!])!,
                    address: columnData[columns["Address"]!]
                )
                shelters.append(Shelter(
                    id: Int(columnData[columns["Unique Key"]!])!,
                    name: columnData[columns["Shelter Name"]!],
                    capacity: columnData[columns["Capacity"]!].count > 0 ? Int(columnData[columns["Capacity"]!])! : 0,
                    notes: columnData[columns["Special Notes"]!],
                    phone: columnData[columns["Phone Number"]!],
                    location: location,
                    restrictions: restrictions
                ))
            } catch let error {
                print("invalid regex: \(error.localizedDescription)")
                return []
            }
        }
        return shelters
    }
    
    private static func processRestrictions(restrictionsString: String) -> ShelterRestrictionList {
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
