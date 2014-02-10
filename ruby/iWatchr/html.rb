def parse_standard_report_pdf(file, has, client, dt, name, nbr)
  text     = pdf_to_text(file)
  rpt_date = nil
  rpt_name = nil
  rpt_nbr  = nil
  rpt_client   = nil
 
  text.each_index do |idx|
    ln = text[idx]
    if ln =~ /Report #/ and not rpt_nbr
      rpt_nbr = text[idx + 1]
    elsif ln =~ /Client Name/ and not rpt_name
      rpt_name = text[idx + 1]
    elsif ln =~ /Report Date/ and not rpt_date
      rpt_client = text[idx + 1]
      rpt_date = text[idx + 2]
    elsif has.has_key?(ln)
      has[ln] = idx
    end
  end
 
  if rpt_client
    if rpt_client == client
      puts "Found correct client name: #{rpt_client}"
    else
      puts "Found wrong client name: #{rpt_client}: expected #{client}"
    end
  else
    puts "Client name not found"
  end
  if rpt_nbr
    if rpt_nbr == nbr
      puts "Found correct report number: #{rpt_nbr}"
    else
      puts "Found wrong report number: #{rpt_nbr}: expected #{nbr}"
    end
  else
    puts "Report number not found"
  end
  if rpt_name
    if rpt_name == name
      puts "Found correct report name: #{rpt_name}"
    else
      puts "Found wrong report name: #{rpt_name}: expected #{name}"
    end
  else
    puts "Report name not found"
  end
  if rpt_date
    if rpt_date == dt
      puts "Found correct report date: #{rpt_date}"
    else
      puts "Found wrong report date: #{rpt_date}: expected #{dt}"
    end
  else
    puts "Report date not found"
  end
  has.each_key do |key|
    msg = "Find #{key}"
    if has[key] > 0
      #~ passed_to_log(msg)
      puts msg + " passed."
    else
      puts msg + " FAILED."
    end
  end
end