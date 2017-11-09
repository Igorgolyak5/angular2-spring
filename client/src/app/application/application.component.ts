import {Component, OnInit, ViewChild} from '@angular/core';
import {ApplicationService} from "./application.service";
import {ModalComponent} from "../modal/modal.component";
import {NotificationService} from "../_services/notification.service";
import {SelectLoaderComponent} from "../select-loader/select.loader.component";

@Component({
    selector: 'application',
    templateUrl: 'application.component.html'
})
export class ApplicationComponent implements OnInit {

    apps: any = [];
    model: any = {};
    newModel: any =  {user: {}};
    multiple: boolean = true;

    @ViewChild("editAppModal") editAppModal:ModalComponent;
    @ViewChild("createAppModal") createAppModal:ModalComponent;
    @ViewChild("freePublishers") freePublishers:SelectLoaderComponent;
    constructor(private applicationService: ApplicationService,
                private notificationService: NotificationService) {
    }

    ngOnInit(): void {
        this.refreshAll();
    }

    refreshAll() {
        this.applicationService.getAll().subscribe(application => {
            this.apps = application.json();
        });
    }

    getAll() {
        return this.apps;
    }

    delete(id: String) {
        this.applicationService.delete(id).subscribe(response => {
            this.notificationService.success("The app has been removed");
            this.refreshAll();
        });
    }

    update(app: any) {
        this.applicationService.update(app).subscribe(response => {
            this.notificationService.success("The app has been updated");
            this.refreshAll();
            this.editAppModal.hide();
        });
    }

    create(app: any) {
        this.applicationService.create(app).subscribe(response => {
            this.notificationService.success("The app has been added");
            this.refreshAll();
            this.freePublishers.refreshAll();
            this.createAppModal.hide();
        });
    }

    editModalShow(app: any) {
        this.model = Object.assign({}, app);
        this.editAppModal.show();
    }

    createModalShow() {
        this.createAppModal.show();
    }
}
