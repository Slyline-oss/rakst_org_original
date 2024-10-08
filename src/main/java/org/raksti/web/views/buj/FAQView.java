package org.raksti.web.views.buj;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.raksti.web.data.entity.FAQ;
import org.raksti.web.data.service.FAQService;
import org.raksti.web.views.MainLayout;

import java.util.List;

@PageTitle("BUJ")
@Route(value = "buj", layout = MainLayout.class)
@AnonymousAllowed
public class FAQView extends VerticalLayout {
    private final List<FAQ> faqList;

    public FAQView(FAQService faqService) {
        faqList = faqService.getAll();
        addClassNames("faq-view");
        getStyle().set("padding-top", "30px");
        landing();
    }

    private void landing() {

        //Div wrapper
        Div wrapper = new Div();
        wrapper.addClassNames("faq-wrapper");
        //Image div
        Div image = new Div();
        image.addClassNames("image");
        //Image
        Image img = new Image("images/img.png", "main img");
        img.addClassNames("image-img");
        image.add(img);
        //Horizontal layout
        HorizontalLayout hl = new HorizontalLayout();
        hl.addClassNames("faq-entries");
        hl.setJustifyContentMode(JustifyContentMode.BETWEEN);
        hl.setSpacing(true);

        for (FAQ value : faqList) {
            Div question = new Div();
            question.addClassNames("faq-entry-question");
            Div answer = new Div();
            answer.addClassNames("faq-entry-answer");
            question.setText(value.getQuestion());
            answer.setText(value.getAnswer());
            wrapper.add(assignClass(new Div(question, answer)));
        }
        hl.add(wrapper, image);
        add(hl);
    }

    private Div assignClass(Div div) {
        div.addClassNames("faq-item");
        return div;
    }
}
