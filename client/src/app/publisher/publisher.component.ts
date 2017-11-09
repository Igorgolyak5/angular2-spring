import {Component, OnInit, ViewChild} from '@angular/core';
import {PublishersService} from "./publisher.service";
import {ModalComponent} from "../modal/modal.component";
import {NotificationService} from "../_services/notification.service";

@Component({
    selector: 'publishers',
    templateUrl: 'publisher.component.html'
})
export class PublishersComponent implements OnInit {

    publishers: any = [];
    model: any = {};
    isMultiple: boolean = true;

    @ViewChild("editUserModal") editUserModal:ModalComponent;
    @ViewChild("createUserModal") createUserModal:ModalComponent;
    constructor(private publisherService: PublishersService, private notificationService: NotificationService) {}

    ngOnInit(): void {
        this.refreshAll();
    }

    getAll() {
        return this.publishers;
    }

    refreshAll() {
        this.publisherService.getAll().subscribe(publishers => {
            this.publishers = publishers.json();
        });
    }

    create(operator: any) {
        this.publisherService.create(operator).subscribe(response=>{
            this.notificationService.success("The publisher has been added");
            this.refreshAll();
        });
    }

    update(operator: any) {
        this.publisherService.update(operator).subscribe(response=>{
            this.notificationService.success("The publisher has been updated");
            this.refreshAll();
            this.editUserModal.hide();
        });
    }

    delete(id: String) {
        this.publisherService.delete(id).subscribe(response=>{
            this.notificationService.success("The publisher has been deleted");
            this.refreshAll();
        });
    }

    editModalShow(publisher: any) {
        this.model = Object.assign({}, publisher);
        this.model.password = null;
        this.editUserModal.show();
    }

    createModalShow() {
        this.model = {};
        this.createUserModal.show();
    }
}
