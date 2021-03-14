package ru.rseu.gorkin.web;

import ru.rseu.gorkin.datalayer.dao.DBType;
import ru.rseu.gorkin.resources.utils.ConfigurationManager;
import ru.rseu.gorkin.web.commands.CommandsFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {
    public static final String DAO_FACTORY_CONTEXT_ATTRIBUTE = "dao_factory_attribute";

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(DAO_FACTORY_CONTEXT_ATTRIBUTE, DBType.getTypeByName(ConfigurationManager.getProperty("dbtype")).getDAOFactory());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommandsFactory commandsFactory = new CommandsFactory();
        commandsFactory.defineCommand(request).execute(request, response);
    }

}
