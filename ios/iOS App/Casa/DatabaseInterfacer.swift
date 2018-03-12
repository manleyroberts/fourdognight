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
                    restrictionsString: columnData[columns["Restrictions"]!]
                ))
            } catch let error {
                print("invalid regex: \(error.localizedDescription)")
                return []
            }
        }
        return shelters
    }
}
