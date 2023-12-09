package com.paper.service;

import com.paper.dao.ArxivPaperDao;
import com.paper.model.entity.ArxivPaper;
import com.paper.model.entity.ArxivPaperDO;
import com.paper.utils.ArxivParser;
import com.paper.utils.DownloadUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
public class PaperServiceTest {
    @Resource
    private ArxivPaperService paperService;
    @Resource
    private ArxivPaperDao arxivPaperDao;

    @Test
    public void getArxivPaperFromOfficialApi() {
        paperService.getArxivPaperFromOfficialApi(0);
    }

    @Test
    public void parseXMLTest() {
        LinkedList<ArxivPaper> parse = ArxivParser.parse(response);

    }

    String response = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<feed xmlns=\"http://www.w3.org/2005/Atom\">\n" +
            "  <link href=\"http://arxiv.org/api/query?search_query%3Dcat%3Amath.HO%26id_list%3D%26start%3D0%26max_results%3D2\" rel=\"self\" type=\"application/atom+xml\"/>\n" +
            "  <title type=\"html\">ArXiv Query: search_query=cat:math.HO&amp;id_list=&amp;start=0&amp;max_results=2</title>\n" +
            "  <id>http://arxiv.org/api//dNTxNKi9QGPygVi/vrUCDXams4</id>\n" +
            "  <updated>2023-04-08T00:00:00-04:00</updated>\n" +
            "  <opensearch:totalResults xmlns:opensearch=\"http://a9.com/-/spec/opensearch/1.1/\">3288</opensearch:totalResults>\n" +
            "  <opensearch:startIndex xmlns:opensearch=\"http://a9.com/-/spec/opensearch/1.1/\">0</opensearch:startIndex>\n" +
            "  <opensearch:itemsPerPage xmlns:opensearch=\"http://a9.com/-/spec/opensearch/1.1/\">2</opensearch:itemsPerPage>\n" +
            "  <entry>\n" +
            "    <id>http://arxiv.org/abs/2304.02365v1</id>\n" +
            "    <updated>2023-04-05T11:02:59Z</updated>\n" +
            "    <published>2023-04-05T11:02:59Z</published>\n" +
            "    <title>Modeling still matters: a surprising instance of catastrophic floating\n" +
            "  point errors in mathematical biology and numerical methods for ODEs</title>\n" +
            "    <summary>  We guide the reader on a journey through mathematical modeling and numerical\n" +
            "analysis, emphasizing the crucial interplay of both disciplines. Targeting\n" +
            "undergraduate students with basic knowledge in dynamical systems and numerical\n" +
            "methods for ordinary differential equations, we explore a model from\n" +
            "mathematical biology where numerical methods fail badly due to catastrophic\n" +
            "floating point errors. We analyze the reasons for this behavior by studying the\n" +
            "steady states of the model and use the theory of invariants to develop an\n" +
            "alternative model that is suited for numerical simulations. Our story intends\n" +
            "to motivate combining analytical and numerical knowledge, even in cases where\n" +
            "the world looks fine at first sight. We have set up an online repository\n" +
            "containing an interactive notebook with all numerical experiments to make this\n" +
            "study fully reproducible and useful for classroom teaching.\n" +
            "</summary>\n" +
            "    <author>\n" +
            "      <name>Cordula Reisch</name>\n" +
            "    </author>\n" +
            "    <author>\n" +
            "      <name>Hendrik Ranocha</name>\n" +
            "    </author>\n" +
            "    <arxiv:comment xmlns:arxiv=\"http://arxiv.org/schemas/atom\">17 pages, 10 figures</arxiv:comment>\n" +
            "    <link href=\"http://arxiv.org/abs/2304.02365v1\" rel=\"alternate\" type=\"text/html\"/>\n" +
            "    <link title=\"pdf\" href=\"http://arxiv.org/pdf/2304.02365v1\" rel=\"related\" type=\"application/pdf\"/>\n" +
            "    <arxiv:primary_category xmlns:arxiv=\"http://arxiv.org/schemas/atom\" term=\"math.HO\" scheme=\"http://arxiv.org/schemas/atom\"/>\n" +
            "    <category term=\"math.HO\" scheme=\"http://arxiv.org/schemas/atom\"/>\n" +
            "    <category term=\"cs.NA\" scheme=\"http://arxiv.org/schemas/atom\"/>\n" +
            "    <category term=\"math.NA\" scheme=\"http://arxiv.org/schemas/atom\"/>\n" +
            "    <category term=\"37M05, 65L06, 65L20, 65P40, 97D40\" scheme=\"http://arxiv.org/schemas/atom\"/>\n" +
            "  </entry>\n" +
            "  <entry>\n" +
            "    <id>http://arxiv.org/abs/2211.06447v5</id>\n" +
            "    <updated>2023-04-04T16:41:29Z</updated>\n" +
            "    <published>2022-11-11T19:08:59Z</published>\n" +
            "    <title>Modern Light on Ancient Logic</title>\n" +
            "    <summary>  In this paper we study the ancient theory of definition and the five\n" +
            "\\emph{predicabilia} such as found in Aristotle and Porphyry using modern logic.\n" +
            "We draw our inspiration from Bealer's intensional logic $T2$, Kelley-Morse set\n" +
            "theory and the classification and definition practices of modern mathematics.\n" +
            "Finally we propose an alternative approach to formalising Aristotle's Topics\n" +
            "which will be the subject of a future paper.\n" +
            "</summary>\n" +
            "    <author>\n" +
            "      <name>Clarence Protin</name>\n" +
            "    </author>\n" +
            "    <link href=\"http://arxiv.org/abs/2211.06447v5\" rel=\"alternate\" type=\"text/html\"/>\n" +
            "    <link title=\"pdf\" href=\"http://arxiv.org/pdf/2211.06447v5\" rel=\"related\" type=\"application/pdf\"/>\n" +
            "    <arxiv:primary_category xmlns:arxiv=\"http://arxiv.org/schemas/atom\" term=\"math.HO\" scheme=\"http://arxiv.org/schemas/atom\"/>\n" +
            "    <category term=\"math.HO\" scheme=\"http://arxiv.org/schemas/atom\"/>\n" +
            "    <category term=\"math.LO\" scheme=\"http://arxiv.org/schemas/atom\"/>\n" +
            "    <category term=\"03-03, 03A05, 03B45, 03B65\" scheme=\"http://arxiv.org/schemas/atom\"/>\n" +
            "  </entry>\n" +
            "</feed>";


    @Value("${arxiv.paper.pdf.dir:D:/temp/arxiv/pdf/}")
    private String  saveDir;

    @Test
    public void downloadTest() {
        List<ArxivPaperDO> arxivPapers = arxivPaperDao.listArxivPaper();
        int count = 0;
        for (ArxivPaperDO arxivPaper : arxivPapers) {
            count ++;
//            if (count>1){
//                break;
//            }
            System.out.println("downloading :\t"+count);
            String pdfUrl = arxivPaper.getPdfUrl();
            String[] split = pdfUrl.split("/", 0);
            String fileName = split[split.length - 1] + ".pdf";
            try {
                DownloadUtil.downLoadByUrl(pdfUrl.replace("http","https")+".pdf",fileName,saveDir);
            }catch (Exception e){
                System.out.println("fail to download file " +pdfUrl);
                e.printStackTrace();
            }
        }
    }
}