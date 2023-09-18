package org.raksti.web.views.buj;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.jetbrains.annotations.NotNull;
import org.raksti.web.data.entity.FAQ;
import org.raksti.web.data.service.FAQService;
import org.raksti.web.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@PageTitle("Rediģēt BUJ")
@Route(value = "buj-create", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class CreateFAQView extends VerticalLayout {
    private final FAQService faqService;

    @Autowired
    public CreateFAQView(@NotNull FAQService faqService) {
        this.faqService = faqService;
        landing();
    }

    //makes layout of page
    private void landing() {
        Button add = new Button("ADD NEW QUESTION AND ANSWER");
        add.addClickListener(e -> editBlock(new FAQ("", "")));
        this.add(add);

        List<FAQ> blocks = faqService.getAll();
        for (FAQ block : blocks) {
            HorizontalLayout hl = new HorizontalLayout();
            Label label = new Label(block.getQuestion());
            hl.add(label);
            Button edit = new Button("EDIT");
            edit.addClickListener(e -> editBlock(block));
            hl.add(edit);
            Button delete = new Button("DELETE");
            delete.addClickListener(e -> faqService.delete(block));
            hl.add(delete);
            this.add(hl);
        }
    }

    private void editBlock(@NotNull FAQ block) {
        Dialog dialog = new Dialog();

        TextField title = new TextField("Title");
        title.setValue(block.getQuestion());
        title.setWidth(90, Unit.PERCENTAGE);
        TextArea body = new TextArea("Information");
        body.setValue(block.getAnswer());
        body.setWidth(90, Unit.PERCENTAGE);
        VerticalLayout vl = new VerticalLayout(title, body);

        HorizontalLayout buttons = new HorizontalLayout();
        Button ok = new Button("OK");
        ok.addClickListener(e -> {
            block.setQuestion(title.getValue());
            block.setAnswer(body.getValue());
            faqService.save(block);
            dialog.close();
            UI.getCurrent().getPage().reload();
        });
        Button cancel = new Button("Cancel");
        cancel.addClickListener(e -> dialog.close());
        buttons.add(ok, cancel);

        dialog.add(vl, buttons);

        dialog.setWidth(90, Unit.PERCENTAGE);
        dialog.open();
    }

}
