package carami.todo.dao;

public class MemberSqls {
    final static String SELECT_BY_ID = "select id, name, email, passwd from member where id = :id";
    final static String UPDATE_BY_ID = "update member set name = :name , email = :email where id = :id";
    final static String DELETE_BY_ID = "delete from member where id = :id";
    final static String SELECT = "select id, name, email, passwd from member order by id desc limit :start, :count";
}
