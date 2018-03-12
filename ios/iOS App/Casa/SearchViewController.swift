//
//  SearchViewController.swift
//  Casa
//
//  Created by Jared Duncan on 3/12/18.
//  Copyright © 2018 Four Dog Night. All rights reserved.
//

import UIKit
import Foundation

class SearchViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource {
    @IBOutlet weak var agePicker: UIPickerView!
    @IBOutlet weak var genderSelector: UISegmentedControl!
    @IBOutlet weak var nameField: UITextField!
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return Age.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return Age(rawValue: row)?.description
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "searchByAge" {
            let selectedAge = Age(rawValue: agePicker.selectedRow(inComponent: 0))!
            let destination = segue.destination as! MainViewController
            destination.shelters = ShelterAdapter.getSheltersByAge(age: selectedAge)
        } else if segue.identifier == "searchByGender" {
            let male = genderSelector.selectedSegmentIndex == 0
            let destination = segue.destination as! MainViewController
            destination.shelters = ShelterAdapter.getSheltersByGender(male: male)
        } else if segue.identifier == "searchByName" {
            let name = nameField.text!
            let destination = segue.destination as! MainViewController
            destination.shelters = ShelterAdapter.getSheltersByName(name: name)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        agePicker.delegate = self
        agePicker.dataSource = self
    }
}
