package ru.rseu.gorkin.datalayer.dao;

import ru.rseu.gorkin.datalayer.dto.User;

import java.util.List;

public interface UserDAO {
	void block(String login);
	void unblock(String login);
	void delete(String login);
	List<User> getAll();
	User get(int id);
	User get(String login);
	AuthenticationResults authenticate(String login, String password);
}
