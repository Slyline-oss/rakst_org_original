package com.example.application.views.newExam;

import com.example.application.data.service.ExamService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@PageTitle("Exam")
@Route("exam/:naming/")
@RolesAllowed("USER")
public class CreatedExamView extends VerticalLayout implements BeforeEnterObserver {

    private String naming;

    private final ExamService examService;

    public CreatedExamView(ExamService examService) {
        this.examService = examService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        naming = beforeEnterEvent.getRouteParameters().get("naming").get();
        Exam exam = examService.get(naming);
        if (exam == null || exam.isFinished()) {
            beforeEnterEvent.forwardTo("about");
        }
    }
}
