import {Component, OnInit, ViewChild} from '@angular/core';
import {OperatorsService} from "./operator.service";
import {ModalComponent} from "../modal/modal.component";
import {NotificationService} from "../_services/notification.service";

@Component({
    selector: 'operators',
    templateUrl: 'operator.component.html'
})
export class OperatorsComponent implements OnInit {

    operators: any = [];
    model: any = {};

    @ViewChild("editUserModal") editUserModal:ModalComponent;
    @ViewChild("createUserModal") createUserModal:ModalComponent;
    constructor(private operatorSerivce: OperatorsService,
                private notificationService: NotificationService) {}

    ngOnInit(): void {
        this.refreshAll();
    }

    getAll() {
        return this.operators;
    }

    refreshAll() {
        this.operatorSerivce.getAll().subscribe(operators => {
            this.operators = operators.json();
        });
    }

    create(operator: any) {
        this.operatorSerivce.create(operator).subscribe(response=>{
            this.notificationService.success("The operator has been added");
            this.refreshAll();
        });
    }

    update(operator: any) {
        this.operatorSerivce.update(operator).subscribe(response=>{
            this.notificationService.success("The operator has been updated");
            this.refreshAll();
            this.editUserModal.hide();
        });
    }

    delete(id: String) {
        this.operatorSerivce.delete(id).subscribe(response=>{
            this.notificationService.success("The operator has been deleted");
            this.refreshAll();
        });
    }

    editModalShow(operator: any) {
        this.model = Object.assign({}, operator);
        this.model.password = null;
        this.editUserModal.show();
    }

    createModalShow() {
        this.model = {};
        this.createUserModal.show();
    }
}
