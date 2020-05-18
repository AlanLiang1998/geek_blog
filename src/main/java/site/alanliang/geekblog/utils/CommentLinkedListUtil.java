package site.alanliang.geekblog.utils;

import site.alanliang.geekblog.model.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Descriptin TODO
 * @Author AlanLiang
 * Date 2020/5/17 21:49
 * Version 1.0
 **/
public class CommentLinkedListUtil {
    public static List<Comment> toLinkedList(List<Comment> rootList, List<Comment> commentList) {
        ArrayList<Comment> linkedList = new ArrayList<>();
        for (Comment parent : rootList) {
            List<Comment> children = new ArrayList<>();
            findChildren(children, parent, commentList);
            parent.setChildren(children);
            linkedList.add(parent);
        }
        return linkedList;
    }

    private static void findChildren(List<Comment> children, Comment parent, List<Comment> commentList) {
        for (Comment child : commentList) {
            if (parent.getId().equals(child.getPid())) {
                children.add(child);
                findChildren(children, child, commentList);
            }
        }
    }
}
