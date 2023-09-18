package org.raksti.web.views.about;

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
import org.raksti.web.data.entity.About;
import org.raksti.web.data.service.AboutService;
import org.raksti.web.views.MainLayout;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@PageTitle("Rediģēt Sākumlapu")
@Route(value = "create-aboutview", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class CreateAboutView extends VerticalLayout {
    private final AboutService aboutService;

    public CreateAboutView(AboutService aboutService) {
        this.aboutService = aboutService;
        landing();
    }

    private void landing() {
        Button add = new Button("ADD NEW INFORMATION");
        add.addClickListener(e -> editBlock(new About("", "")));
        this.add(add);

        List<About> blocks = aboutService.getAll();
        for (About block : blocks) {
            HorizontalLayout hl = new HorizontalLayout();
            Label label = new Label(block.getTitle());
            hl.add(label);
            Button edit = new Button("EDIT");
            edit.addClickListener(e -> editBlock(block));
            hl.add(edit);
            Button delete = new Button("DELETE");
            delete.addClickListener(e -> aboutService.delete(block));
            hl.add(delete);
            this.add(hl);
        }
    }

    private void editBlock(@NotNull About block) {
        Dialog dialog = new Dialog();

        TextField title = new TextField("Title");
        title.setValue(block.getTitle());
        title.setWidth(90, Unit.PERCENTAGE);
        TextArea body = new TextArea("Information");
        body.setValue(block.getText());
        body.setWidth(90, Unit.PERCENTAGE);
        VerticalLayout vl = new VerticalLayout(title, body);

        HorizontalLayout buttons = new HorizontalLayout();
        Button ok = new Button("OK");
        ok.addClickListener(e -> {
            block.setTitle(title.getValue());
            block.setText(body.getValue());
            aboutService.save(block);
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
