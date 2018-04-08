//
//  MainScreen.swift
//  Casa
//
//  Created by Isaac Weintraub on 2/22/18.
//  Copyright © 2018 Four Dog Night. All rights reserved.
//

import UIKit

class MainViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    @IBOutlet weak var shelterListView: UITableView!
    var shelters = ShelterAdapter.getAllShelters()
    private weak var selectedShelter: Shelter?
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return shelters.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell(style: UITableViewCellStyle.default, reuseIdentifier: "cell")
        cell.textLabel?.text = shelters[indexPath.row].name
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        self.selectedShelter = shelters[indexPath.row]
        performSegue(withIdentifier: "shelterDetailSegue", sender: self)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if (segue.identifier == "shelterDetailSegue") {
            let shelterDetail = segue.destination as! ShelterDetailViewController
            shelterDetail.shelter = self.selectedShelter
        }
    }
    
    
    //MARK: Properties
    @IBOutlet weak var usernameLabel: UILabel!
    var nametext: String = ""
    
    //MARK: Actions
    @IBAction func logout(_ sender: Any) {
        let splashViewController: UIViewController = UIStoryboard(name: "SplashScreen", bundle: nil).instantiateViewController(withIdentifier: "splash")
        self.present(splashViewController, animated: true, completion: nil)
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        self.shelterListView.delegate = self
        self.shelterListView.dataSource = self
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        usernameLabel.text = nametext
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func passUser(user: AbstractUser) {
        nametext = "\(user.name) | \(user.username) | " + ((user is Admin) ? "Admin" : "User")
    }
}
