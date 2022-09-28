package org.raksti.web.views.buj;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.raksti.web.data.entity.FAQ;
import org.raksti.web.data.service.FAQService;
import org.raksti.web.views.MainLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

@PageTitle("BUJ")
@Route(value = "buj", layout = MainLayout.class)
@AnonymousAllowed
public class FAQView extends VerticalLayout {

    private final static Logger logger = LoggerFactory.getLogger(FAQView.class);

    private final List<FAQ> faqList;

    public FAQView(FAQService faqService) {
        faqList = faqService.getAllFAQ();
        addClassNames("faq-view");
        landing();
    }


    private void landing() {
        //Div wrapper
        HorizontalLayout hl = new HorizontalLayout();
        hl.addClassNames("faq-entries");
        hl.setJustifyContentMode(JustifyContentMode.BETWEEN);
        hl.setFlexGrow(2);
        hl.setSpacing(true);

        for (FAQ value : faqList) {
            Div question = new Div();
            question.addClassNames("faq-entry-question");
            Div answer = new Div();
            answer.addClassNames("faq-entry-answer");
            question.setText(value.getQuestion());
            answer.setText(value.getAnswer());
            logger.info("Content added " + value.getQuestion());

            hl.add(new Div(question, answer));
        }

        add(hl);
    }
}
