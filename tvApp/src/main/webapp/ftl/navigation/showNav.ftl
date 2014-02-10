<#macro leftNav showEpisode=true>
    <div id="leftnav">
        <ul>
            <li><a href="<@s.url action="viewTvShowHub"/>">All TV Shows</a></li>
            <#if showEpisode>
                <li><a href="<@s.url action="viewTvShowEpisode">
                    <@s.param name="tvShowName"><@s.property value="tvShowName" /></@s.param>
                        </@s.url>">
                        View all <@s.property value="tvShowName" /> episodes
                    </a>
                </li>
            </#if>
            <li><a href="<@s.url action="updateTvShow">
                <@s.param name="tvShowName"><@s.property value="tvShowName" /></@s.param>
                    </@s.url>">
                    Update <@s.property value="tvShowName" />
                </a>
            </li>
        </ul>
    </div>
</#macro>