<%@page import="com.game.common.basics.pagination.PageQuery"%>
<%@page import="com.game.common.basics.pagination.PageKeys"%>
<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%
	PageQuery p = (PageQuery)request.getAttribute(PageKeys.PAGINATION_KEY);
%>
<TD noWrap bgColor=#d7e4f7 colSpan=16 border="0">
	<TABLE cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
		<TBODY>
			<TR>
				<td>
					<table border="0" align="middle" cellpadding="0" cellspacing="0">
						<tr>
							<td width=600 align=right>
								共计：<%=p.getTotalCount()%>条记录&nbsp;页次：<%=p.getPageNo()%></STRONG>/<%=p.getTotalPages()%>&nbsp;每页：${p.getPageSize} 条
							</td>
							<td width="40">
								<a href="?<%=PageKeys.PAGINATION_CURR_PAGE%>=1&<%=PageKeys.PARAMETER_KEY%>=<%=p.getSearchKeys()%>"><img src="${ctx}/resources/admin/images/first.gif" width="37" height="15" /></a>
							</td>
							<td width="45">
								<a href="?<%=PageKeys.PAGINATION_CURR_PAGE%>=<%=p.getPrePage()%>&<%=PageKeys.PARAMETER_KEY%>=<%=p.getSearchKeys()%>"><img src="${ctx}/resources/admin/images/back.gif" width="43" height="15" /></a>
							</td>
							<td width="45">
								<a href="?<%=PageKeys.PAGINATION_CURR_PAGE%>=<%=p.getNextPage()%>&<%=PageKeys.PARAMETER_KEY%>=<%=p.getSearchKeys()%>"><img src="${ctx}/resources/admin/images/next.gif" width="43" height="15" /></a>
							</td>
							<td width="40">
								<a href="?<%=PageKeys.PAGINATION_CURR_PAGE%>=<%=p.getTotalPages()%>&<%=PageKeys.PARAMETER_KEY%>=<%=p.getSearchKeys()%>"><img type="image" src="${ctx}/resources/admin/images/last.gif" width="37" height="15" /></a>
							</td>
							<td width="40"></td>
						</tr>
					</table>
				</td>
			</TR>
		</TBODY>
	</TABLE>
</TD>
